function getAll(){
    const url = 'http://localhost:8080/api';
    const container = $('#tbody');
    $.ajax({
        url: url,
        dataType: 'json',
        type: "GET",
    }).done((response) => {

        let html = '';

        response.forEach((item) => {
            let trHtml =
                `<tr>
                    <td>${item.id}</td>
                    <td>${item.name}</td>
                    <td>${item.lastname}</td>
                    <td>${item.age}</td>
                    <td>${item.email}</td>
                    <td>${item.roles}</td>
                    <td><a id="buttonEditUser" role="button" onclick="getEditModal(${item.id})" class="btn btn-primary btn-sm" data-target="#buttonEditUser">Edit</a></td>
                    <td><a id="buttonDeleteUser" role="button" onclick="getDeleteModal(${item.id})" class="btn btn-danger btn-sm" >Delete</a></td>
                </tr>`;
            html += trHtml;
        })

        container.html(html);

    })
}

function getEditModal(id){
    $.ajax({
        url: 'http://localhost:8080/api/' + id + '/show',
        dataType: 'json',
        type: 'GET'
    }).done(user => {
                let html = document.getElementById("modalEdit");
                html.innerHTML = `<div id="edit" class="modal fade" tabIndex="-1" role="dialog" 
                              aria-labelledby="TitleModalLabel" aria-hidden="true"
                              data-backdrop="static" data-keyboard="false">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Edit user</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="formEditUser" 
                              class="form-signin mx-auto font-weight-bold text-center"
                              style="width: 200px;">
                            <p>
                                <label for="del_id">ID</label>
                                <input class="form-control form-control-sm" type="text" name="id" id="del_id"
                                       readOnly value="${user.id}">
                            </p>
                            <p><label for="name">Name</label>
                                <input type="text" name="name" value="${user.name}" id="name"></p>
                            <p><label for="lastname">Lastname</label>
                                <input type="text" name="lastname" value="${user.lastname}" id="lastname"></p>
                            <p><label for="Username">Username</label>
                                <input type="text" name="username" value="${user.username}" id="Username"></p>
                            <p><label for="Password">Password</label>
                                <input type="password" name="password" value="${user.password}" id="Password"></p>
                            <p><label for="age">Age</label>
                                <input type="number" name="age" value="${user.age}" id="age"></p>
                            <p><label for="email">Email</label>
                                <input type="text" name="email" value="${user.email}" id="email"></p>
                            <p>
                            <label>Role</label>
                                <select id="roles" name="roles" multiple ="2" required
                                               class="form-control form-control-sm">
                                        <option value="1">ADMIN</option>
                                        <option value="2">USER</option>
                                </select>
                            </p>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close
                                    </button>
                                    <button type="button" 
                                            class="btn btn-primary js-edit-user">Edit
                                    </button>
                                </div>
                                </form>
                    </div>
                </div>
            </div>
        </div>`
                $('#edit').modal();
                $('.js-edit-user').click(function () {
                    editUser(user);
                })
            })
    function editUser(user){

        const form = document.getElementById('formEditUser');
        const formData = new FormData(form);
        const data = {};

        let values = $('#roles').val();
        for (let key of formData.keys()) {
            data[key] = formData.get(key);
        }
        $.ajax({
                url: 'http://localhost:8080/api/update?roles='+ values,
                type: 'PUT',
                contentType: 'application/json',
                processData: false,
                cash: false,
                data: JSON.stringify(data)
            }
        ).always(function (){
            getAll();
            $('#edit').modal('hide');
        })
    }
}
$(document).ready(() => {
    getAll();
})
function getDeleteModal(id) {
    $.ajax({
        url: 'http://localhost:8080/api/' + id + '/show',
        dataType: 'json',
        type: 'GET'
    }).done(user => {
        let adminSelect = "";
        let userSelect = "";

        let html = document.getElementById("modalEdit");
        html.innerHTML = `<div id="edit" class="modal fade" tabIndex="-1" role="dialog" 
                              aria-labelledby="TitleModalLabel" aria-hidden="true"
                              data-backdrop="static" data-keyboard="false">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Delete user</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="formDeleteUser" 
                              class="form-signin mx-auto font-weight-bold text-center"
                              style="width: 200px;">
                            <p>
                                <label for="del_id">ID</label>
                                <input class="form-control form-control-sm" type="text" name="id" id="del_id"
                                       readOnly value="${user.id}">
                            </p>
                            <p><label for="name">Name</label>
                                <input type="text" name="name" readonly value="${user.name}" id="name"></p>
                            <p><label for="lastname">Lastname</label>
                                <input type="text" name="lastname" readOnly value="${user.lastname}" id="lastname"></p>
                            <p><label for="Username">Username</label>
                                <input type="text" name="username" readOnly value="${user.username}" id="Username"></p>
                            <p><label for="Password">Password</label>
                                <input type="password" name="password" readOnly value="${user.password}" id="Password"></p>
                            <p><label for="age">Age</label>
                                <input type="number" name="age" readOnly value="${user.age}" id="age"></p>
                            <p><label for="email">Email</label>
                                <input type="text" name="email" readOnly value="${user.email}" id="email"></p>
                            <br>
                            <p>
                            <label>Role</label>
                                <select id="editRoles" name="role" multiple size="2" required
                                               class="form-control form-control-sm">
                                        <option value="${user.roles}">ADMIN</option>
                                        <option value="${user.roles}">USER</option>
                                </select>
                            </p>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close
                                    </button>
                                    <button type="button" 
                                            class="btn btn-danger js-edit-user">Delete
                                    </button>
                                </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>`
        $('#edit').modal();
        $('.js-edit-user').click(function () {
            editUser(user);
        })
    })
    function editUser(user) {
        const form = document.getElementById('formDeleteUser');
        const formData = new FormData(form);
        const data = {};

        for (let key of formData.keys()) {
            data[key] = formData.get(key);
        }
        $.ajax({
                url: 'http://localhost:8080/api/delete/' + id + '',
                type: 'DELETE',
                contentType: 'application/json',
                processData: false,
                cash: false,
                data: JSON.stringify(data)
            }
        ).always(function () {
            getAll();
            $('#edit').modal('hide');
        })
    }
}
function addUser(){
    let user = {
        name: $('#new_user #firstname').val(),
        lastname: $('#new_user #lastname').val(),
        age: $('#new_user #age').val(),
        email: $('#new_user #email').val(),
        password: $('#new_user #password').val()
    }
    let values = $('#addroleList').val();

    $.ajax({
            url: 'http://localhost:8080/api/create?roles='+ values,
            type: 'POST',
            contentType: 'application/json',
            processData: false,
            cash: false,
            data: JSON.stringify(user)
        }
    ).done(function () {
        getAll();
        $('.ustable').click()
    })
}

