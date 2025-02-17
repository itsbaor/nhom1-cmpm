import React from "react";
import styles from './Footer.module.scss'
import classNames from 'classnames/bind';
import {AiOutlineTwitter, AiOutlineInstagram} from 'react-icons/ai'
import {FaFacebookF} from "react-icons/fa"
import {FiSend} from "react-icons/fi"

const cx=classNames.bind(styles)
function Footer () {
    return ( 
        <div className={cx('keep-travle')}>
        <div className={cx('keep-header')}>
        </div>
        <div className={cx('keep-body')}>
         <div className={cx('keep-contact')}>
         <p className={cx('keep-icon')}>MOVIE.</p>
         <p>This website will help you to know more about the movie news that is running as well as being shown in many countries, due to some financial reasons, we cannot buy copyrighted movies. Happy New Year to you all.</p>
        </div>
        <div className={cx('keep-list')}>
           <div className={cx('footer-contact')}>
            <p className={cx('contact-us')}>CONTACT US</p>
            <div className={cx('contact-onl')}>
                <input placeholder="Enter your email ..."/>
                <FiSend className={cx('icon-send')}/>
            </div>  
            <div className={cx('keep-ti')}>
              <i><FaFacebookF className={cx('icon-fabook')}/></i>
              <i><AiOutlineInstagram  className={cx('icon-is')}/></i>
              <i><AiOutlineTwitter  className={cx('icon-tw')}/></i>
           </div>
           </div>
        </div>
        </div>
        <div className={cx('keep-footer')}>
            <p className={cx('keep-footer-fist')}>THE BEST WORDPRESS MOVIE THEME</p>
            <p className={cx('keep-footer-second')}>COPYRIGHT BY BAOTC@2003</p>
        </div>
    </div>
 );
}
export default Footer
