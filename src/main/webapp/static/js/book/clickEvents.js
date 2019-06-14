$("#title-sort").click(function () {
  if (state === 'title_asc') {
    sortBooks(state);
    $("#title-arrow").text('arrow_drop_down');
    state = 'title_dsc';
  } else {
    state = 'title_dsc';
    sortBooks(state);
    $("#title-arrow").text('arrow_drop_up');
    state = 'title_asc';
  }
});

$("#author-sort").click(function () {
  if (state === 'author_asc') {
    sortBooks(state);
    $("#author-arrow").text('arrow_drop_down');
    state = 'author_dsc';
  } else {
    state = 'author_dsc';
    sortBooks(state);
    $("#author-arrow").text('arrow_drop_up');
    state = 'author_asc';
  }
});

$("#genre-sort").click(function () {
  if (state === 'genre_asc') {
    sortBooks(state);
    $("#genre-arrow").text('arrow_drop_down');
    state = 'genre_dsc';
  } else {
    state = 'genre_dsc';
    sortBooks(state);
    $("#genre-arrow").text('arrow_drop_up');
    state = 'genre_asc';
  }
});

$("#house-sort").click(function () {
  if (state === 'house_asc') {
    sortBooks(state);
    $("#house-arrow").text('arrow_drop_down');
    state = 'house_dsc';
  } else {
    state = 'house_dsc';
    sortBooks(state);
    $("#house-arrow").text('arrow_drop_up');
    state = 'house_asc';
  }
});

$("#date-sort").click(function () {
  if (state === 'publ_date_asc') {
    sortBooks(state);
    $("#date-arrow").text('arrow_drop_down');
    state = 'publ_date_dsc';
  } else {
    state = 'publ_date_dsc';
    sortBooks(state);
    $("#date-arrow").text('arrow_drop_up');
    state = 'publ_date_asc';
  }
});

$("#count-sort").click(function () {
  if (state === 'count_asc') {
    sortBooks(state);
    $("#count-arrow").text('arrow_drop_down');
    state = 'count_dsc';
  } else {
    state = 'count_dsc';
    sortBooks(state);
    $("#count-arrow").text('arrow_drop_up');
    state = 'count_asc';
  }
});