import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import RowMovies from "./RowMovies";
import * as ACTION from "../store/actions"
import ScrollTop from "../store/actions/ScrollTop";

function Content (props){
    const dispatch=useDispatch();
    const {
        NetFlixOriginal,
        TrendingMovie,
        TopRateMovie,
        ActionsMovie,
        ComedyMovie,
        HorrorMovie,
        RomanceMovie,
        DocumentMovie,
    }= useSelector(state => state.inforMovies);
    useEffect(()=>{
        dispatch(ACTION.getNetflixOriginal())
        dispatch(ACTION.getTrendingMovie())
        dispatch(ACTION.getTopRateMovie())
        dispatch(ACTION.getActionMovie())
        dispatch(ACTION.getComedyMovie())
        dispatch(ACTION.getHorrorMovie())
        dispatch(ACTION.getRomanceMovie())
        dispatch(ACTION.getDocumentaryMovie())
        dispatch(ACTION.getSearchMovie())
    },[dispatch])
    return(
        <div>
            <RowMovies movies={NetFlixOriginal} name="Netflix Originals" isNetflix={true} idSecsion='netflixMovie'/>
            <RowMovies movies={TrendingMovie} name="Trending" idSecsion='trendingMovie'/>
            <RowMovies movies={TopRateMovie} name="Top Rated" idSecsion='toprateMovie'/>
            <RowMovies movies={ActionsMovie} name="Actions Movies" idSecsion='actionMovie'/>
            <RowMovies movies={ComedyMovie} name="Comedy Movies" idSecsion='comedyMovie'/>
            <RowMovies movies={HorrorMovie} name="Horror Movies" idSecsion='HorrorMovie'/>
            <RowMovies movies={RomanceMovie} name="Romance Movies" idSecsion='RomaceMovie'/>
            <RowMovies movies={DocumentMovie} name="Documentaries" idSecsion='documenMovie'/>
            <ScrollTop/>
        </div>
    )
}
export default Content
