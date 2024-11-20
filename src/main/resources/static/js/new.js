const zoom = () => {
    if($(".sidebar").is(":visible")){
        $(".sidebar").css("display","none");
        $(".content").css("margin-left","2%");

    }else{
        $(".sidebar").css("display","block");
        $(".content").css("margin-left","20%");
    }
};
const search=()=>
{
	let query=$("#search-input").val();
	
	if(query == "")
	{
		$(".search-result").hide();
	}
	else
	{
		console.log(query);
		
		//sending request to server
		let url=`http://localhost:8080/search/${query}`;
		
		fetch(url)
		.then((response)=>{
			return response.json();
		})
		.then((data)=>
		{
			//data..
			
			let text=`<div class='list-group'> `;
			
			data.forEach((contact)=>{
				text+=`<a href='/user/contact/${contact.cid}' class='list-group-item list-group-item-action'> ${contact.name} </a> `
			});
			
			
			text += `</div>`;
			
			$(".search-result").html(text);
			$(".search-result").show();
			
		});
		
		
		$(".serach-result").show();
	}
	
	
	
};