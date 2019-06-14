function removeChip(nameId) {
  $('input[name="' + nameId + '"]').prop('checked', false);
  localStorage.removeItem(nameId);
  $('.filter_form').submit();
}
