import classNames from 'classnames/bind';
import styles from './menuItem.module.scss';
import { RandomColorRGB } from '../../../utilis';
import { Link } from 'react-scroll';

const cx= classNames.bind(styles)

function MenuItem (props) {
    const {Name, Icon, to}=props
    return ( 
        <Link
         className={cx('menuItem')}
         to={to}
         spy={true}
         smooth={true}
         offset={-70}
         duration={500}
         activeClass="active"
       >
               <Icon className={cx('menuIcon')} style={{color:RandomColorRGB(1)}}/>
               <span className={cx('menuDesc')}>{Name}</span>
          </Link>
 );
}
export default MenuItem
