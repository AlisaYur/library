$(document).ready(function () {
  let errorMessage = document.getElementById('error-message').innerHTML;
  if (errorMessage.length > 0) {
    M.toast({html: errorMessage, classes: 'rounded'});
  }
});