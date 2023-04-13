$(document).ready(function() {
    var botao = $('.bt1');
    var dropDown = $('.ul-funcionarios');    
   
    botao.on('click', function(event){
        dropDown.stop(true,true).slideToggle();
        event.stopPropagation();
    });
});

$(document).ready(function() {
    var botao = $('.bt2');
    var dropDown = $('.ul-produtos');    
   
    botao.on('click', function(event){
        dropDown.stop(true,true).slideToggle();
        event.stopPropagation();
    });
});

$(document). read(function associaInput() {
    var variavel;
    //pega o value do select
    var e = document.getElementById("tipo_entrada");
    var itemSelecionado = e.options[e.selectedIndex].value;
     //injeta no value do input
    if (itemSelecionado=="produto novo"){
        variavel="NOVO";
    }else if (itemSelecionado=="produto quebrado"){
        variavel="QUEBRADO";
    }
    document.getElementById('camada').value = variavel;
    console.log(variavel);
});