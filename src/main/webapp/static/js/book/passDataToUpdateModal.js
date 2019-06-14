function passData(id, title, genre, author, publishingHouse, date,
    countInStock) {
  $(document).ready(function () {
    $('.modal2').modal();
    document.getElementById('update-id').value = id;
    document.getElementById('update-title').value = title;
    document.getElementById('update-genre-' + genre).selected = true;
    document.getElementById('update-author-' + author).selected = true;
    document.getElementById('update-genre-' + genre).defaultSelected = true;
    document.getElementById('update-author-' + author).defaultSelected = true;
    $('select').formSelect();
    document.getElementById('update-genre-' + genre).defaultSelected = false;
    document.getElementById('update-author-' + author).defaultSelected = false;
    document.getElementById('update-date').value = date;
    document.getElementById('update-publ-house').value = publishingHouse;
    document.getElementById('update-count').value = countInStock;
  });
}
