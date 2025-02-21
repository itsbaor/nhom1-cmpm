import React from 'react';
import SearchOutlinedIcon from '@mui/icons-material/SearchOutlined';

const RightPart = () => {
    return (
        <div className='bg-gray-400 rounded-xl mt-10'>
            <SearchOutlinedIcon/>
            <input type="text" placeholder='Search' className='bg-gray-400 text-black' />
        </div>
    );
}

export default RightPart;
