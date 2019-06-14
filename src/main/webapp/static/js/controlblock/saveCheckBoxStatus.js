$('#filter_tag').empty();
$("input[type=checkbox]").each(function () {
  let name = $(this).attr('name');

  if (localStorage.getItem(name) === "true") {
    $(this).prop('checked', true);
    $('#filter_tag').append(
        '<div class="chip">' + $(this).val()
        + '<a href="#" onclick="removeChip(' + "\x27" + $(this).attr('name')
        + "\x27" + ')">' +
        '<i class="close material-icons">close</i></a></div>'
    );
  }
});

$("input[type=checkbox]").change(function () {

  let name = $(this).attr('name'),
      value = $(this).is(':checked');
  localStorage.setItem(name, value);
});
