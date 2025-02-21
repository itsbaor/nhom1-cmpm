export const formatTimeDifference = (createdAt) => {
    const createdDate = new Date(createdAt);  // Ngày tạo của bài post
    const now = new Date();  // Ngày hiện tại
    const diffInMs = now - createdDate;  // Khoảng cách thời gian tính bằng mili giây
    
    // Chuyển mili giây sang phút, giờ, ngày, tuần, và tháng
    const diffInMinutes = Math.floor(diffInMs / (1000 * 60));
    const diffInHours = Math.floor(diffInMs / (1000 * 60 * 60));
    const diffInDays = Math.floor(diffInMs / (1000 * 60 * 60 * 24));
    const diffInWeeks = Math.floor(diffInMs / (1000 * 60 * 60 * 24 * 7));
    const diffInMonths = Math.floor(diffInMs / (1000 * 60 * 60 * 24 * 30));  // khoảng 30 ngày
  
    // Kiểm tra và trả về kết quả dựa trên mốc thời gian
    if (diffInMonths >= 1) return `${diffInMonths} tháng`;
    if (diffInWeeks >= 1) return `${diffInWeeks} tuần`;
    if (diffInDays >= 1) return `${diffInDays} ngày`;
    if (diffInHours >= 1) return `${diffInHours} giờ`;
    if (diffInMinutes >= 1) return `${diffInMinutes} phút`;
  
    return `Vừa xong`;  // Nếu khoảng cách nhỏ hơn 1 phút
  }
  