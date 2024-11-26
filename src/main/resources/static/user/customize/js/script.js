// $('loginForm').on('submit', function (event){
//     event.preventDefault(); // ngan chan form submit mac dinh
//     $.ajax({
//         url: '/api/auth/log-in',
//         type: 'POST',
//         contentType: 'application/json',
//         data: JSON.stringify({
//             username: $('#email').val(),
//             password: $('#password').val()
//         }),
//         success: function (response){
//             //  Lưu token vào local storage hoặc session storage
//             localStorage.setItem('authToken', response.token);
//             $('#message').text('Đăng nhập thành công');
//         },
//         error: function (){
//             $('message').text('Đăng nhập thất bại. Vui lòng thu lai!');
//         }
//     })
// });
//

// Get user credential details
$(document).ready(function() {
    $.ajax({
        url: '/api/auth/user/details',
        type: 'GET',
        headers: { "Authorization": "Bearer " + localStorage.getItem('authToken') },
        success: function(user) {
            // Hiển thị thông tin người dùng trong form
            $('#username').text(user.email);
            $('#password').text(user.firstName);
        },
        error: function() {
            $('#message').text('Không thể tải thông tin người dùng.');
        }
    });
});

$(document).read(function(){
    $.ajax({
        url: '/api/auth/user/details',
        type: 'GET',
        header: {'Authorization':"Bearer " + localStorage.getItem('authToken')},
        success: function (user) {
            // Hien thi thong tin nguoi dung tu form
            $('#username').text(user.username);
            $('#password').text(user.password);
        },
        error: function (){
            $('#message').text('Khong the tai thong tin nguoi dung');
        }
    });
});

document.addEventListener("DOMContentLoaded", function() {
    const registerForm = document.getElementById("registerForm");

    // Bắt sự kiện submit
    registerForm.addEventListener("submit", function(event) {
        event.preventDefault(); // Ngăn hành vi submit mặc định
        registerUser(); // Gọi hàm xử lý đăng ký qua AJAX
    });
});
function registerUser() {
    let user = {
        email: document.getElementById('email').value,
        password: document.getElementById('password').value,
        confirmPassword: document.getElementById('confirmPassword').value,
        phoneNumber: document.getElementById('phoneNumber').value,
        firstName: document.getElementById('firstname').value,
        lastName: document.getElementById('lastname').value,
        gender: document.querySelector('input[name="gender"]:checked').value,
        dayOfBirth: document.getElementById('dob').value
    };

    fetch("/api/users/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(user)
    })
        .then(response => response.json())
        .then(data => {
            if (response.ok) {
                alert(data); // Register successfully
            }else {
                let errors = "";
                for (const [key, value] of Object.entries(data)){
                    errors += `${key}: ${value}\n`;
                }
                alert(errors)
            }
        })
        .catch(error => console.error("Error: ", error))
}


$(document).ready(function (){
    const isWishlistEmpty = $('#is-wishlist-empty').val() === 'true';
    if (isWishlistEmpty){
        $('#wishlist-items-container').hide();
        $('#wishlist-empty-message').fadeIn();
    }else{
        $('#wishlist-items-container').show();
    }
});

$(document).on('submit', '.remove-item', function(event) {
    event.preventDefault(); // Ngăn form submit bình thường
    // Lấy dữ liệu từ form
    const $form = $(this);
    const wishlistId = $form.find('input[name="wishlistId"]').val();
    const productId = $form.find('input[name="productId"]').val();

    // Gửi yêu cầu AJAX DELETE
    $.ajax({
        url: '/user/wishlist/remove',
        type: 'DELETE',
        data: { wishlistId: wishlistId, productId: productId },
        success: function(response) {
            alert(response); // Hiển thị thông báo thành công
            $form.closest('tr').remove(); // Xoá dòng sản phẩm khỏi giao diện
            // Kiểm tra nếu giỏ hàng trống
            if ($('#cart-container table tbody tr').length === 0) {
                $('#cart-container table').hide();
                $('#cart-empty-message').fadeIn();
            }
        },
        error: function(xhr) {
            alert(xhr.responseText || 'Có lỗi xảy ra, vui lòng thử lại.');
        }
    });
});

$(document).on('submit', '.clear-wishlist', function (event){
    event.preventDefault();

    const $form = $(this);
    const wishlistId = $form.find('input[name="wishlistId"]').val();

    if (confirm("Are your sure you want to clear all items from the wishlist?")){
        $.ajax({
            url: '/user/wishlist/clear',
            type: 'POST',
            data: {wishlistId: wishlistId},
            success: function (response){
                // Xoa toan bo thu muc ra khoi bang
                const container = $('#wishlist-items-container')
                container.hide();
                alert(response);
                // Hiển thị thông báo wishlist trống
                $('#wishlist-empty-message').fadeIn();
            },
            error: function (xhr){
                alert(xhr.responseText || 'Co loi, Vui long thu lai');
            }
        });
    }
});