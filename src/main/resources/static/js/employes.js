// Call the dataTables jQuery plugin
$(document).ready(function() {
    loadEmployes();
  $('#employes').DataTable();
});


async function loadEmployes(){
    
    const rawResponse = await fetch('/employes', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    });
    const employes = await rawResponse.json();
    
    let listHTML = '';
    
    for(let employe of employes){
        
        let employeshtml = 
        '<tr><td>'+employe.id+'</td><td>'+employe.name+'</td><td>'+employe.email+'</td><td>'+employe.role+'</td><td>'+employe.enterprise+'</td><td><a href="#"class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a></td></tr>';
        listHTML += employeshtml
    }
    
    console.log(employes);

    document.querySelector('#employes tbody').outerHTML = listHTML;
}