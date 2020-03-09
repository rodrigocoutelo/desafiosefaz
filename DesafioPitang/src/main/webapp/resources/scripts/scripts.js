// Hide submenus
$('#body-row .collapse').collapse('hide');

// Collapse/Expand icon
$('.collapse-icon').addClass('fa-angle-double-left');

// Collapse click
$(".encolhe").click(function() {
	console.log("Opa");
	SidebarCollapse();
});

function SidebarCollapse() {
	$('.menu-collapsed').toggleClass('d-none');
	$('.sidebar-submenu').toggleClass('d-none');
	$('.submenu-icon').toggleClass('d-none');
	$('#sidebar-container').toggleClass('sidebar-expanded sidebar-collapsed');

	// Treating d-flex/d-none on separators with title
	var SeparatorTitle = $('.sidebar-separator-title');
	if (SeparatorTitle.hasClass('d-flex')) {
		SeparatorTitle.removeClass('d-flex');
	} else {
		SeparatorTitle.addClass('d-flex');
	}

	// Collapse/Expand icon
	$('#collapse-icon').toggleClass(
			'fa-angle-double-left fa-angle-double-right');
}

$(document).ready(function() {

	$(".maskPhone").focus(function(event) {
		$(".maskPhone").mask('00000.0000');
	});

	$(".maskPhone").blur(function(event) {
		if ($(this).val().length == 10) {
			$(".maskPhone").mask('00000.0000');
		} else {
			$(".maskPhone").mask('0000.0000');
		}
	});
});