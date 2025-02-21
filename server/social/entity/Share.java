package com.social.social.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Share {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String content;

    public Share() {}
    public Share(String title, String content) {
        this.title = title;
        this.content = content;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

//entity đại diên bài viết với các thuộc tính cơ bản
//Repository cung cấp phương thức để truy vấn bài viết tư cơ sở dữ liệu
//Service Xử lý logic nghiệp vụ trong trường hợp này lấy bài viết theo id
//Controller yêu cầu HTTP và trả về URL chia sẻ bài viết

// truy cập bài viết thông qua API thông qua endpoint
//GET http://localhost:8080/api/posts/1/share
//==> kết quả Chia sẻ bài viết: http://localhost:8080/api/posts/1