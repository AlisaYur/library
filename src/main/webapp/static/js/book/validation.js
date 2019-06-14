let dateRegex = /[12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])/;
let isValidated = false;

$(document).ready(function () {
  $("#submitBook").click(function () {
    event.preventDefault();
    checkCreateFields();
    if (isValidated === true) {
      checkTitleCreate();
    }
  });

  $("#updateBook").click(function () {
    event.preventDefault();
    checkUpdateFields();
  });

  function checkTitleCreate() {
    let data = {};
    data["title"] = $("#create-book-title").val();
    let requestData = JSON.stringify(data);
    $.ajax({
      type: 'POST',
      contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
      data: {requestData},
      dataType: 'json',
      url: "/mainMenu?action=CheckBookTitle",
      success: function (response) {
        if (response.title === 'book found!') {
          $("#submitBook").css("background-color", "red");
          M.toast({
            html: 'Book with this title already exists!',
            classes: 'rounded'
          })
        } else if (response.title === 'invalid input!') {
          $("#submitBook").css("background-color", "red");
          M.toast({html: 'Incorrect input!', classes: 'rounded'})
        } else {
          $(".bookForm").submit();
        }
      },
      error: function (e) {
        $("#submitBook").css("background-color", "red");
      }
    });
  }

  function checkCreateFields() {
    let title = document.getElementById('create-book-title').value;
    let date = document.getElementById('create-book-date').value;
    let publishingHouse = document.getElementById('create-book-publH').value;
    let countInStock = document.getElementById('create-book-count').value;
    let genre = document.getElementById('create-book-genre');
    let author = document.getElementById('create-book-author');
    isValidated = false;

    if (title.length === 0 || date.length === 0 || publishingHouse.length === 0
        || countInStock.length === 0) {
      $("#updateBook").css("background-color", "red");
      M.toast({html: 'All fields are required!', classes: 'rounded'})
    } else if (isNaN(countInStock) || countInStock < 0) {
      M.toast({html: 'Enter correct books count!', classes: 'rounded'})
    } else if (genre.selected === true) {
      M.toast({html: 'Select book genre!', classes: 'rounded'})
    } else if (author.selected === true) {
      M.toast({html: 'Select book author!', classes: 'rounded'})
    } else if (date.search(dateRegex) === -1) {
      M.toast({html: 'Enter correct date!', classes: 'rounded'})
    } else {
      isValidated = true;
    }

  }

  function checkUpdateFields() {
    let title = document.getElementById('update-title').value;
    let date = document.getElementById('update-date').value;
    let publishingHouse = document.getElementById('update-publ-house').value;
    let countInStock = document.getElementById('update-count').value;

    if (title.length === 0 || date.length === 0 || publishingHouse.length === 0
        || countInStock.length === 0) {
      $("#updateBook").css("background-color", "red");
      M.toast({html: 'All fields are required!', classes: 'rounded'})
    } else if (date.search(dateRegex) === -1) {
      M.toast({html: 'Enter correct date!', classes: 'rounded'})
    } else if (isNaN(countInStock) || countInStock < 0) {
      M.toast({html: 'Enter correct books count!', classes: 'rounded'})
    } else {
      $(".bookFormUpdate").submit();
    }
  }
});
