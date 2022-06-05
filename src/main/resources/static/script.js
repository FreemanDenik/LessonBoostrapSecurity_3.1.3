let editModal = document.getElementById('editModal')
editModal.addEventListener('show.bs.modal', function (event) {
    let button = event.relatedTarget
    let id = button.getAttribute('data-bs-id')
    let username = button.getAttribute('data-bs-username')
    let email = button.getAttribute('data-bs-email')

    let inputId = editModal.querySelector('.modal-body #id')
    let InputUsername = editModal.querySelector('.modal-body #username')
    let InputEmail = editModal.querySelector('.modal-body #email')

    inputId.value = id
    InputUsername.value = username
    InputEmail.value = email
})
let deleteModal = document.getElementById('deleteModal')
deleteModal.addEventListener('show.bs.modal', function (event) {
    let button = event.relatedTarget
    let id = button.getAttribute('data-bs-id')
    let username = button.getAttribute('data-bs-username')
    let email = button.getAttribute('data-bs-email')

    let inputHiddenId = deleteModal.querySelector('.modal-body #id')
    let inputId = deleteModal.querySelector('.modal-body #labelId')
    let InputUsername = deleteModal.querySelector('.modal-body #username')
    let InputEmail = deleteModal.querySelector('.modal-body #email')

    inputId.value = id
    inputHiddenId.value = id;
    InputUsername.value = username
    InputEmail.value = email
})