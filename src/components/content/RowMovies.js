import classNames from 'classnames/bind';
import styles from './RowMovies.module.scss';
import { useEffect, useRef, useState } from 'react';
import {BsArrowLeftCircle,BsArrowRightCircle} from 'react-icons/bs'
import {SmoothHorizontaScrooling} from '../../utilis'
import { useViewPort } from '../hooks/useViewPort';
import { useDispatch } from 'react-redux';
import { getMovieDetail } from '../store/actions';

const cx=classNames.bind(styles)
function RowMovies (props) {
    const {movies, name, isNetflix, idSecsion}=props;
    const sliderRef=useRef();
    const movieRef=useRef();
    const [isdrag, setIsDrag]=useState(false)
    const [dragDown, setDragDown]= useState(0)
    const[dragMove, setDragMove]= useState(0)
    const [windowWidth]=useViewPort()

    const dispatch=useDispatch()
    const handelMovie=(movie)=>{
        dispatch(getMovieDetail(movie))
     }
    //handle Scroll movieItem
    const handleScrollRight=()=>{
        const maxScrollLeft =sliderRef.current.scrollWidth -  sliderRef.current.clientWidth
        if(sliderRef.current.scrollLeft<maxScrollLeft){
            SmoothHorizontaScrooling(
                 sliderRef.current, 250,
                 movieRef.current.clientWidth *2,
                 sliderRef.current.scrollLeft)
        }

    }
    const handleScrollLeft=()=>{
        if(sliderRef.current.scrollLeft > 0){
            SmoothHorizontaScrooling(
                 sliderRef.current, 250,
                 -movieRef.current.clientWidth *2,
                 sliderRef.current.scrollLeft)
        }
    }
//handle onDragItem
   useEffect(()=>{
           if(isdrag){
             if(dragMove<dragDown){handleScrollRight()}
             if(dragMove>dragDown){handleScrollLeft()}
           }
   },[isdrag,dragDown,dragMove])
   const onDragStart= e=>{
        setIsDrag(true)
        setDragDown(e.screenX)
   }
   const onDragEnd= e=>{
         setIsDrag(false)
   }
   const onDragEnter= e=>{
        setDragMove(e.screenX)
   }
    return ( 
        <div className={cx('container')} draggable='false' id={idSecsion}>
            <h1 className={cx('contentName')}>{name}</h1>
            <div className={cx('contentWrap')} 
                style={movies && movies.length>0
                     ?{
                        gridTemplateColumns:`repeat(${movies.length},
                            ${ windowWidth>1200 ?"360px"
                             : windowWidth>992 ?"300px"
                             : windowWidth>768 ?"250px":"200px"
                             }
                            )`
                     }:{}}
                 ref={sliderRef} 
                 draggable='true' 
                 onDragStart={onDragStart}
                 onDragEnd={onDragEnd} 
                 onDragEnter={onDragEnter}>
                {movies && movies.length>0 && props.movies.map((movie,index)=>{
                    if(movie.poster_path && movie.backdrop_path !==null){
                        let imageURL= isNetflix
                        ? `https://image.tmdb.org/t/p/original/${movie.poster_path}`
                        : `https://image.tmdb.org/t/p/w500/${movie.backdrop_path}`
                        return(
                           <div 
                           draggable='false'
                           onClick={ ()=> handelMovie(movie)}
                           ref={movieRef}
                           key={index} 
                           className={cx('movieItem')}>
                                 <img src={imageURL} alt='' draggable='false'/>
                                 <div className={cx('movieName')}>{movie.title || movie.name}</div>
                       </div>
                      )
                     }
                }
               )}
            </div>
            <div className={cx('btnLeft', 'btn', isNetflix && 'isNetflixBtn')} onClick={handleScrollLeft}>
                <BsArrowLeftCircle/>
            </div>
            <div className={cx('btnRight', 'btn', isNetflix && 'isNetflixBtn')} onClick={handleScrollRight}>
                <BsArrowRightCircle/>
            </div>

        </div>
 );
}
export default RowMovies