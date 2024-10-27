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