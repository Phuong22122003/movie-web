import { Country } from "./country";
import { Genre } from "./genre";

export interface Movie {
    id: number;
    name?: string;
    description?: string;
    image_url?: string;
    video_url?: string;
    genres?: Genre[];
    country?: Country;
    createdDate?: string;
    viewCount?: number;
}
