function createOrder(id, title) {
  $(document).ready(function () {
    document.getElementById('book-id-order').value = id;
    document.getElementById('book_name').value = title;

    let today = new Date();
    let dd = today.getDate();
    let mm = today.getMonth() + 1; //January is 0!
    let yyyy = today.getFullYear();

    if (dd < 10) {
      dd = '0' + dd
    }

    if (mm < 10) {
      mm = '0' + mm
    }
    today = mm + '/' + dd + '/' + yyyy;
    document.getElementById('currentDate').value = today;
  })
}
