function mostrarFormClassificacao() {
	var display = document.getElementById('divClassificacaoAJAX').style.display;
	if (display == 'none')
		document.getElementById('divClassificacaoAJAX').style.display = 'block';
	else
		document.getElementById('divClassificacaoAJAX').style.display = 'none';
}

function mostrarFormDesenvolvedora() {
	var display = document.getElementById('divDesenvolvedoraAJAX').style.display;
	if (display == 'none')
		document.getElementById('divDesenvolvedoraAJAX').style.display = 'block';
	else
		document.getElementById('divDesenvolvedoraAJAX').style.display = 'none';
	document.querySelector(div).focus();

}

function mostrarFormDiretor() {
	var display = document.getElementById('divDiretorAJAX').style.display;
	if (display == 'none')
		document.getElementById('divDiretorAJAX').style.display = 'block';
	else
		document.getElementById('divDiretorAJAX').style.display = 'none';
	document.querySelector('divDiretorAJAX').focus();

}

function mostrarFormGenero() {
	var display = document.getElementById('divGeneroAJAX').style.display;
	if (display == 'none')
		document.getElementById('divGeneroAJAX').style.display = 'block';
	else
		document.getElementById('divGeneroAJAX').style.display = 'none';
	document.querySelector(div).focus();

}

function mostrarFormPlataforma() {
	var display = document.getElementById('divPlataformaAJAX').style.display;
	if (display == 'none')
		document.getElementById('divPlataformaAJAX').style.display = 'block';
	else
		document.getElementById('divPlataformaAJAX').style.display = 'none';
	document.querySelector(div).focus();

}

function mostrarFormModoJogo() {

	var display = document.getElementById('divModoJogoAJAX').style.display;
	if (display == 'none')
		document.getElementById('divModoJogoAJAX').style.display = 'block';
	else
		document.getElementById('divModoJogoAJAX').style.display = 'none';
	document.querySelector(div).focus();

}

function adicionarClassificacaoAJAX() {
	var classificacao = {
			nomeClassificacao : $("#nomeClassificacao").val()

		}

		// DO POST
	$(document).ready(
			function() {
				$.ajax({
					type : "POST",
					contentType : "application/json",
					url : "/adicionarClassificacaoAJAX",
					data : JSON.stringify(classificacao),
					dataType : 'json',
					complete : function(result) {
						if (result.status == 200) {
							window.alert("SUCESSO");
						} else {
							window.alert("ERRO");
						}
						console.log(result);
					}
				});
			}
			);
	
	

}
