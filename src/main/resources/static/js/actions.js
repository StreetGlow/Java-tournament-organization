/*-----------data show------------*/

$('.fs').on('change', function() {
    $('#tournamentsID').val($('.fs option:selected').attr('data'));
    $('#tournamentsIDshow').val($('.fs option:selected').attr('data1'));
    $('#bracketID').val($('.fs option:selected').attr('data2'));
    $('#roundID').val($('.fs option:selected').attr('data3'));
})
$('#tournamentsID').val($('.fs option:selected').attr('data'));
$('#tournamentsIDshow').val($('.fs option:selected').attr('data1'));
$('#bracketID').val($('.fs option:selected').attr('data2'));
$('#roundID').val($('.fs option:selected').attr('data3'));


/*-----------clickable row------------*/

 $(document).ready(function() {
    $("#table tr").click(function() {
        var href = $(this).find("a").attr("href");
        if(href) {
            window.location = href;
        }
    });
});


/*-----------time picker------------*/

$(document).ready(function(){
    $('#timepicker').timepicker({
    timeFormat: 'HH:mm',
    defaultTime: '12:00',
    interval: 15,
    dynamic: false,
    dropdown: true,
    scrollbar: true
    });
});


/*-----------form validation------------*/

(function () {
'use strict'
const forms = document.querySelectorAll('.requires-validation')
Array.from(forms)
  .forEach(function (form) {
    form.addEventListener('submit', function (event) {
      if (!form.checkValidity()) {
        event.preventDefault()
        event.stopPropagation()
      }
      form.classList.add('was-validated')
    }, false)
  })
})()

/*-----------Clipboard copy------------*/

 function copyToClipboard() {
    var copyText = document.getElementById("content").value;
    navigator.clipboard.writeText(copyText).then(() => {
        alert("Code copied to clipboard");
    });
  }
