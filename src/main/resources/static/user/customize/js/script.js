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