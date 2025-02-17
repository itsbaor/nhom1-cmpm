import React from 'react';
import SearchMovie from '../searchMovie/searchMovie';
import { useSelector } from 'react-redux';

function SearchPage(props) {
    const {MovieDetail}=useSelector(state=>state.inforMovies)

    return (
        <div>
            <SearchMovie movie={MovieDetail} showModel={MovieDetail ? true : false}/>
        </div>
    );
}

export default SearchPage;