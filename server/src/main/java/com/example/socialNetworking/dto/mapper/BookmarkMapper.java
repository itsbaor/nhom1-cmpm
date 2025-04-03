package com.example.socialNetworking.dto.mapper;

import com.example.socialNetworking.dto.BookmarkDto;
import com.example.socialNetworking.model.BookmarkPost;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class, PostsMapper.class})
public interface BookmarkMapper {

    BookmarkMapper INSTANCE = Mappers.getMapper(BookmarkMapper.class);

    BookmarkDto bookmarkToBookmarkDto(BookmarkPost bookmarkPost);
}
