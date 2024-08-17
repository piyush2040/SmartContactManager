/**
 * 
 */
console.log("this is script file");
const toggleSidebar = () => {
	if($(".sidebar").is(":visible"))
		{
			$(".sidebar").css("display","none");
			$(".content").css("margin-left","0%");
			$("#SideBarBars").css("display","block");
		}
		else{
			$(".sidebar").css("display","block");
			$(".content").css("margin-left","20%");
			$("#SideBarBars").css("display","none");
		}
}