import classNames from 'classnames/bind';
import styles from './modelMovie.module.scss';
import { getMovieDetail } from '../store/actions';
import { useDispatch } from 'react-redux';
import {BsYoutube} from 'react-icons/bs'
import { useNavigate } from 'react-router-dom';

const cx= classNames.bind(styles)

function ModelMovie(props) {
      const history=useNavigate()

      const handelMovie=()=>{
            alert("I dont have API for movie video. THIS IS DEMO!! :))")
            setTimeout(()=>{
                  history('/movieMM')
                 },1000)
      }
      const {movie, showModel}=props
      const dispatch = useDispatch()
      const handelCloseModal=()=>{
            dispatch(getMovieDetail(null))
      }
    return (
       movie &&  <div className={cx('modelWrap' )}>
      <div onClick={(movie)=> handelCloseModal(movie)} 
          className={ cx('modelMain',`${showModel === true ? 'showBackdrop' : 'hideBackdrop'}`)}></div>
      <div className={cx('modelShow', 'model',  `${showModel === true ? 'showModel' : 'hideModel'}`)}
           style={{backgroundImage:`url(https://image.tmdb.org/t/p/w500/${movie.backdrop_path})`}}>
           <div className={cx('modelElement')} >
                <div className={cx('modelInfor')}>
                     <h1  className={cx('modelName')}>{movie.title || movie.name}</h1>
                     <div className={cx('modeRapo')}>
                            <p className={cx('modeRating')}>Rating: {movie.vote_average*10}%</p>
                            <p className={cx('modelPopularity')}>Popularity: {movie.popularity}</p>
                      </div>
                      <p className={cx('modelDate')}>Release date: {movie.release_date || movie.first_air_date}</p>
                      <p className={cx('modelRuntime')}>Runtime: m</p>
                      <p  className={cx('modelDescription')}>{movie.overview}</p>
                </div>
                <div className={cx('movie-watch')}>
                       <BsYoutube className={cx('movie-watch-bs')} onClick={handelMovie}/>
                       <h3>Watch Movie</h3>
                </div>
            </div>
       </div>  
  </div>
 );
}
export default ModelMovie
