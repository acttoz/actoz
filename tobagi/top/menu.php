<link rel="stylesheet" href="<?php echo G5_URL?>/top/style.css" type="text/css" />

<nav id="topmenu">
	<div class="wrap" id="Mmenu">
		<ul>
        <?php
        $sql = " select * from {$g5['group_table']} where gr_device <> 'mobile' order by gr_order ";
        $result = sql_query($sql);
        for ($gi=0; $row=sql_fetch_array($result); $gi++) { // gi 는 group index
        ?>
		<li class="ok1" <?php if($row[gr_id]==$gr_id){echo "style='background:#bababa'";}?>>
            <a href="<?php echo G5_BBS_URL ?>/group.php?gr_id=<?php echo $row['gr_id'] ?>" class="gnb_1da"><?php echo $row['gr_subject'] ?></a>
			<div class="subMenuBox">
			<ul>
                <?php
                $sql2 = " select * from {$g5['board_table']} where gr_id = '{$row['gr_id']}' and bo_device <> 'mobile' order by bo_order ";
                $result2 = sql_query($sql2);
                for ($bi=0; $row2=sql_fetch_array($result2); $bi++) { // bi 는 board index
                ?>
                <li><a href="<?php echo G5_BBS_URL ?>/board.php?bo_table=<?php echo $row2['bo_table'] ?>" class="gnb_2da"><?php echo $row2['bo_subject'] ?></a></li>
                <?php } ?>
			</ul>
		    </div>
		    <div class="clear"></div>				
		</li>
        <?php } ?>
        <?php if ($gi == 0) {  ?><li class="gnb_empty">생성된 메뉴가 없습니다.</li><?php }  ?>

		</ul>
	</div>
</nav>


<script type="text/javascript">
jQuery(function($){
	$.fn.Mmenu = function(options) {
		var opts = $.extend(options);
		var Mmenu = $(this);
		var MmenuList = Mmenu.find('>ul>li');
		var subMenuBox = Mmenu.find('.subMenuBox');
		var subMenuBoxList = subMenuBox.find('>ul>li');
		var menuwidth = $(this).width();

		function showMenu() {
			t = $(this).parent('li');
			subwidth = t.find('.subMenuBox').width();

			if (!t.hasClass('active')) {
				MmenuList.removeClass('active');
				t.addClass('active');
				subMenuBox.hide();
				if (t.position().left + t.find('.subMenuBox').width() > menuwidth) {
					t.find('.subMenuBox').show().css({left:subwidth}).animate({left: (menuwidth - subwidth)-10}, 400);
				} else {
					t.find('.subMenuBox').show().css({left:subwidth}).animate( { left: t.position().left}, 400 );
				}
			}
		}

		function hideMenu() {
			MmenuList.removeClass('active');
			subMenuBox.hide();
			activeMenu();
		}

		function activeMenu() {
			if(opts.ok1) {
				t = MmenuList.eq(opts.ok1-1); 
				subwidth = t.find('.subMenuBox').width();
				t.addClass('active');
				if (t.position().left + t.find('.subMenuBox').width() > menuwidth) {
					t.find('.subMenuBox').show().css({left:subwidth}).animate({left: (menuwidth - subwidth)-10}, 400);
				} else {
					t.find('.subMenuBox').show().css({left:subwidth}).animate( { left: t.position().left}, 400 );
				}
			}
		}

		return this.each(function() {
			activeMenu();
			MmenuList.find('>a').mouseover(showMenu).focus(showMenu);
			Mmenu.mouseleave(hideMenu);
		});
	}
});
</script>

<script type="text/javascript"> 
$(document).ready(function(){		
	$('#topmenu .wrap').Mmenu({ ok1: 0 });
});


$('div.menubg a').click(function(){
	var bgBgCol = jQuery(this).attr('href');
	var menuHeight = jQuery(this).attr('mheight');
		$('div.menubg a').removeClass('current');
		$(this).addClass('current');
		$('#topmenu,,.wrap,#topmenu a.active,#mainmenu li.ok1').height(menuHeight);

							$('#topmenu .wrap').height(menuHeight + 30);
			$('#topmenu .wrap div.subMenuBox').css({top:menuHeight+'px'}).height(30);
						$('#menubgVal').val(bgBgCol);
		$('#topmenuHeight').val(menuHeight);
	return false;
});
</script>