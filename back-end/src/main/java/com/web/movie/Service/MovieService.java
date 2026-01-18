package com.web.movie.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.movie.Config.CustomUserDetails;
import com.web.movie.Config.JwtTokenProvider;
import com.web.movie.CustomException.NotFoundException;
import com.web.movie.Dto.MovieDto;
import com.web.movie.Dto.request.MovieRequestDto;
import com.web.movie.Entity.Country;
import com.web.movie.Entity.Genre;
import com.web.movie.Entity.Movie;
import com.web.movie.Entity.MovieHeroSlot;
import com.web.movie.Repository.CountryRepository;
import com.web.movie.Repository.FavoriteRepository;
import com.web.movie.Repository.GenreRepository;
import com.web.movie.Repository.MovieHeroSlotRepository;
import com.web.movie.Repository.MovieRepository;
import com.web.movie.mapper.MovieMapper;

import jakarta.transaction.Transactional;

@Service
public class MovieService {
    @Autowired private MovieRepository movieRepository;
    @Autowired private FileService fileService;
    @Autowired private GenreRepository genreRepository;
    @Autowired private MovieMapper movieMapper;
    @Autowired private CountryRepository countryRepository;
    @Autowired private MovieHeroSlotRepository movieHeroSlotRepository;
    @Autowired private FavoriteRepository favoriteRepository;
    @Autowired private JwtTokenProvider jwtTokenProvider;
    @Value("${app.url}")private String baseResource;
    private List<MovieDto> toMovieDtos(List<Movie> movies){
        List<MovieDto> movieDtos = movieMapper.toMovieDtos(movies);
        for(int i = 0; i < movieDtos.size();i++){
            var movie = movieDtos.get(i);
            movie.setImage_url(baseResource + "/resource/images/" + movies.get(i).getImageFileName());
            movie.setVideo_url(baseResource + "/resource/videos/" + movies.get(i).getVideoFileName());
        }
        return movieDtos;
    }
    private MovieDto toMovieDto(Movie movie){
        MovieDto movieDto = movieMapper.toMovieDto(movie);
        movieDto.setImage_url(baseResource + "/resource/images/" + movie.getImageFileName());
        movieDto.setVideo_url(baseResource + "/resource/videos/" + movie.getVideoFileName() + "?token=" + jwtTokenProvider.generateTokenForResourceAccess());
        return movieDto;
    }
    private List<MovieDto> checkIsFavoritedFiled(List<MovieDto> movies){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return movies;
    }
        List<Integer> movieIds = movies.stream().map(movie -> movie.getId()).toList();
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        movieIds = favoriteRepository.filterFavoritedMovieIds(user.getId(), movieIds);
        for(var movie: movies){
            if(movieIds.contains(movie.getId())){
                movie.setIsFavorited(true);
            } 
        }
        return movies;
    }
    public List<Movie> findAllMovies(){
        return movieRepository.findAll();
    }

    public List<MovieDto> findMovies(int offset,int limit){
        List<Movie> movies = movieRepository.findAll(PageRequest.of(offset/limit, limit)).getContent();
        return checkIsFavoritedFiled(toMovieDtos(movies));
    }

    @Transactional
    @CachePut(value="movies",key = "#result.id")
    public MovieDto addMovie(MultipartFile image, MultipartFile video, MovieRequestDto movieDto){
        String imagePath = null;
        String videoPath = null;
        try{
            imagePath = fileService.saveImage(image);
            videoPath = fileService.saveVideo(video);
        }
        catch(Exception e){
            
            fileService.deletImage(imagePath);
            fileService.deletImage(videoPath);
            throw new RuntimeException("Can not save file");
        }
        List<Genre> genres = null;
        Country country = null;
        if(movieDto.getGenreIds()!=null) genres = genreRepository.findAllById(movieDto.getGenreIds());
        if(movieDto.getCountryId()!=null) country = countryRepository.findById(movieDto.getCountryId()).get();
        Movie movie = movieMapper.toMovie(movieDto);
        movie.setImageFileName(imagePath);
        movie.setVideoFileName(videoPath);
        movie.setLength((int) video.getSize());
        movie.setCountry(country);
        movie.setGenres(genres);
        movie.setCreatedDate(LocalDateTime.now());
        movie.setViewCount(0);
        var savedMovie = movieRepository.save(movie);
   
        // fileService.deletImage(imagePath);
        // fileService.deletImage(videoPath);
        return toMovieDto(savedMovie);
    }

    @Transactional
    public MovieDto update(int id, MovieRequestDto movieDto, MultipartFile image, MultipartFile video){
        Movie movie = movieRepository.findById(id).orElseThrow(()->new NotFoundException("Movie not found"));
        String preImageFilename = movie.getImageFileName();
        String preVideoFileName = movie.getVideoFileName();

        if(image!=null&&!image.isEmpty()){
            try{
                String imageName = fileService.saveImage(image);
                movie.setImageFileName(imageName);
            }
            catch(Exception ex){
                throw ex;
            }
        }

        if(video!=null&&!video.isEmpty()){
            try{
                String videoName = fileService.saveVideo(video);
                movie.setVideoFileName(videoName);
            }
            catch(Exception ex){
                fileService.deletImage(movie.getImageFileName());
                throw ex;
            }
        }
        if(movieDto.getGenreIds()!=null){
            List<Genre> genres = genreRepository.findAllById(movieDto.getGenreIds());
            movie.setGenres(genres);
        }
        if(movieDto.getCountryId()!=null){
            Country country = countryRepository.findById(movieDto.getCountryId()).orElse(null);
            movie.setCountry(country);
        }
        movie.setDescription(movieDto.getDescription());
        movie.setName(movieDto.getName());
        
        try {
            movieRepository.save(movie);
        } catch (Exception ex) {
            if(image!=null&&!image.isEmpty())
                fileService.deletImage(movie.getImageFileName());
            if(video!=null&&!video.isEmpty())
                fileService.deleteVideo(movie.getVideoFileName());
            throw ex;
        }

        if(image!=null&&!image.isEmpty()){
            fileService.deletImage(preImageFilename);
        }
        if(video!=null&&!video.isEmpty()){
            fileService.deleteVideo(preVideoFileName);
        }
        

        return toMovieDto(movie);
    }
    public void deleteMovie(Integer id){
        int rowAffected = movieRepository.deleteMovieById(id);
        if(rowAffected==0) throw new NotFoundException("Movie not found");
    }
    @Cacheable(value = "movies",key = "#id")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public MovieDto findMovieById(Integer id){
        Movie movie = movieRepository.findById(id).orElseThrow(()->new NotFoundException("Movie not found"));
        return toMovieDto(movie);
    }
    public List<MovieDto> findMoviesByGenre(String name, int offset, int limit){
        var genre = genreRepository.findByName(name).orElseThrow(()->new NotFoundException("Genre not found"));
        List<Movie> movies = movieRepository.findMovieByGenreId(genre.getId(),offset, limit);
        return checkIsFavoritedFiled(toMovieDtos(movies));
    }

    public List<MovieDto> findMoviesByCountry(String country, int offset,int limit){
        
        List<Movie> movies = movieRepository.findByCountryName(country, PageRequest.of(offset/limit, limit)).getContent();
        return checkIsFavoritedFiled(toMovieDtos(movies));

    }
    public Page<MovieDto> searchMovies(String keyword, int pageNumber, int pageSize){
        Page<Movie> page = movieRepository.searchMovies(keyword, PageRequest.of(pageNumber, pageSize));
        List<Movie> movies = page.getContent();
        Page<MovieDto> movieDtos = new PageImpl<>(checkIsFavoritedFiled(toMovieDtos(movies)), page.getPageable(), page.getTotalElements());
        return movieDtos;
    }

    public List<MovieDto> getMovieSlot(){
        List<MovieHeroSlot> movieHeroSlots = movieHeroSlotRepository.findCurrentSlot();
        List<Movie> movies = movieHeroSlots.stream().map(s-> s.getMovie()).toList();
        if(movies.isEmpty()){
            int SLOT_NUMBER = 10;
           movies = movieRepository.getCurrentMovie(SLOT_NUMBER);
        }
        return checkIsFavoritedFiled(toMovieDtos(movies));
    }
    
    public List<MovieDto> getRecentlyUpdated(int limit){
        List<Movie> movies = movieRepository.getCurrentMovie(limit); 
        return checkIsFavoritedFiled(toMovieDtos(movies));
    }
    
}
