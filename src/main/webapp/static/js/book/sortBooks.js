let state = 'title_asc';

function sortBooks(state) {
  let data = {};
  data["title"] = state;
  $.ajax({
    type: 'POST',
    contentType: 'application/json',
    dataType: 'json',
    url: "/mainMenu?action=SortBooks&sort=" + data["title"],

    success: function (response) {
      let role = response.role;
      console.log(response);
      $("div#grid_books").empty();

      for (let i = 0; i < response.books.length; i++) {
        let books = response.books[i];
        let book;
        let penaltyOrder;
        let img;
        if (books.image != null) {
          img = '<img class="image_book" src="' + books.encodedImage
              + '" alt="BookImage" width="245px" height="380px">';
        } else {
          img = '<img class="image_book" src="/static/images/liabrary-booknotfound.png" '
              +
              'alt="BookImage" width="245px" height="380px">';
        }
        if (books.countInStock === 0) {
          book = '<div id="' + books.id + '" class="book_element disabled">' +
              img +
              '<div class="info_book_grid">' +
              '<h5 class="center title">' + books.title + '</h5>' +
              '</div>';
        } else {
          book = '<div id="' + books.id + '" class="book_element">' +
              img +
              '<div class="info_book_grid">' +
              '<h5 class="center title">' + books.title + '</h5>' +
              '</div>';
        }
        if (response.readerHavePenalty === true) {
          penaltyOrder = '<a href="#" class="modal-trigger link_buy_book disabled_buy_link tooltipped" '
              +
              'data-position="bottom" data-tooltip="We have a penalty!">' +
              '<i class="material-icons buy_icon">shopping_basket</i></a>';
        } else {
          penaltyOrder = '<a href="#createOrder" class="modal-trigger link_buy_book" '
              +
              'onclick="createOrder(' + books.id + ', ' + books.title + ')">' +
              '<i class="material-icons buy_icon">shopping_basket</i></a>';
        }

        if (role === 'ADMIN_ROLE') {
          book = book.concat(
              '<div class="admin_panel_book">' +
              '<p class="center caption_book_author">' + books.author.firstName
              + ' ' + books.author.lastName + '</p>' +
              '<div class="center elements_panel">' +
              '<form method="POST" action="' + '/books/delete?id=' + books.id
              + '">' +
              '<button type="submit" class="delete_book_btn"><i class="material-icons red-text">delete</i></button>'
              +
              '</form>' +
              '<a href="#modal2" class="modal-trigger updateModal" ' +
              'onclick="passData(' + "\x27" + books.id + "\x27" + ', ' +
              "\x27" + books.title + "\x27" + ', ' +
              "\x27" + books.genre.id + "\x27" + ', ' +
              "\x27" + books.author.id + "\x27" + ', ' +
              "\x27" + books.publishingHouse + "\x27" + ', ' +
              "\x27" + books.dateOfPublication + "\x27" + ', ' +
              "\x27" + books.countInStock + "\x27"
              + ')"><i class="material-icons">edit</i>' +
              '</a>' +
              '</div>' +
              '</div>'
          );
        } else if (role === 'READER_ROLE') {
          book = book.concat(
              '<div class="payment_block">' +
              '<div class="center book_btn">' +
              penaltyOrder +
              '</div>' +
              '<p class="center caption_book_author">' + books.author.firstName
              + ' ' + books.author.lastName + '</p>' +
              '</div>'
          );
        } else if (role === 'LIBRARIAN_ROLE') {
          book = book.concat(
              '<div class="payment_block">' +
              '<div class="center book_btn">' +
              '</div>' +
              '<p class="center caption_book_author">' + books.author.firstName
              + ' ' + books.author.lastName + '</p>' +
              '</div>'
          );
        } else {
          book = book.concat(
              '<div class="payment_block">' +
              '<div class="center book_btn">' +
              '<a href="/signin" class="link_buy_book">' +
              '<i class="material-icons buy_icon">shopping_basket</i>' +
              '</a>' +
              '</div>' +
              '<p class="center caption_book_author">' + books.author.firstName
              + ' ' + books.author.lastName + '</p>' +
              '</div>'
          );
        }

        book = book.concat(
            '<div class="book_footer">' +
            '<div class="footer_caption">' +
            '<p class="center caption_book"><span class="footer_category">Genre: </span>'
            + books.genre.name + '</p>' +
            '<p class="center caption_book"><span class="footer_category">Publish House: </span>'
            + books.publishingHouse + '</p>' +
            '<p class="center caption_book"><span class="footer_category">Date of Publication: </span>'
            + books.dateOfPublication + '</p>' +
            '<p class="center caption_book"><span class="footer_category">Count In Stock: </span>'
            + books.countInStock + '</p>' +
            '</div>' + '</div>' + '</div>'
        );
        $("div#grid_books").append(book);
      }
    },
    error: function (e) {
      console.log(e);
    }
  });
}
