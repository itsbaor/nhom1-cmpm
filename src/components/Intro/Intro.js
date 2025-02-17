import ReactPlayer from "react-player"
import classNames from 'classnames/bind';
import styles from './Intro.module.scss';
import {VscMute, VscUnmute} from 'react-icons//vsc'
import { useState } from "react";
const cx=classNames.bind(styles)
function Intro(props){
    const [mute, setMute]=useState(true)
       return(
        <div className={cx('wrapper')}>
            <ReactPlayer
            playing={true}
            loop={true}
            volume={1}
            url="https://vimeo.com/644494272"
            muted={mute}
            width="100%"
            height="100%"
            className={cx('introVideo')}
            />
            <div className={cx('intro')}>
                <h1  className={cx('introInfor')}> Netflix + Endesa</h1>
                <p  className={cx('introDecr')}>
                Dir Pablo Maestres & Eric Morales
                  Prod Co. Primo Content Spain
                  Client Endesa & Netflix
               Shot in Madrid on Sony Venice Leica R lenses , also Sony FX3 FX6 footage and Laowa lens probe
                </p>
            </div>
            {
                mute?(
                    <VscMute className={cx('introUnmute')} 
                    onClick={()=>setMute(prev=>!prev)}/>
                    ):(
                        <VscUnmute className={cx('introUnmute')}
                    onClick={()=>setMute(prev=>!prev)}/>
                )
            }
            <div  className={cx('btRadien')}></div>
        </div>
       )
}

export default Intro