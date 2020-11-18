
package es.uniovi.eii.sdm.datos.server.movieList;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@SuppressWarnings("SpellCheckingInspection")
public class MovieListResult {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private List<es.uniovi.eii.sdm.datos.server.movieList.MovieData> MovieData = null;


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<es.uniovi.eii.sdm.datos.server.movieList.MovieData> getMovieData() {
        return MovieData;
    }

    public void setResults(List<es.uniovi.eii.sdm.datos.server.movieList.MovieData> movieData) {
        this.MovieData = movieData;
    }

}
