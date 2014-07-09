<!DOCTYPE html>

<html lang="ko">
    <head>

        <!-- BOOTSCROLLER premium Bootstrap html5 template v 1.0.0.
        ArtLabs 2013 - http://www.sitediscount.ru
        ####### Released under GNU GENERAL PUBLIC LICENSE
        Version 2 ############ -->

        <meta charset="u tf-8">
        <title>과학평가</title>
        <meta
        name="viewport"
        content="width=device-width, initial-scale=1.0,  user-scalable=no"
        />
        <meta
        name="description"
        content="responsive parallax Bootstrap template"
        >
        <meta
        name="author"
        content="ArtLabs"
        >

        <!-- ######################## Styles ######################
        =======================================================-->
        <link
        href="assets/css/animate.min.css"
        rel="stylesheet"
        >
        <link
        href="assets/css/bootstrap.css"
        rel="stylesheet"
        >
        <link
        href="assets/css/bootstrap-responsive.css"
        rel="stylesheet"
        >
        <script src='./dist/lib/jquery-1.10.2.min.js'></script>
        <link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.css" />
        <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
        <script src="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.js"></script>
        <script src='./dist/ui/widget-scrollview.js'></script>
        <link
        rel='stylesheet'
        href='./dist/ui/widget-chart.css'
        />
        <link
        rel='stylesheet'
        href='./dist/ui/widget-media.css'
        />
        <link
        rel='stylesheet'
        href='./dist/ui/widget-scrollview.css'
        />
        <link
        rel='stylesheet'
        href='./dist/ui/widget-datatable.css'
        />
        <link
        rel='stylesheet'
        href='./dist/ui/widget-editor.css'
        />
        <link
        rel='stylesheet'
        href='./etc.css'
        />
        <script src='./dist/ui/widget-chart.js'></script>
        <script src='./dist/ui/widget-datatable.js'></script>
        <script src='./dist/ui/widget-editor.js'></script>
        <script src='./dist/ui/widget-listview.js'></script>
        <script src='./dist/ui/widget-media.js'></script>
        <script src='./dist/ui/widget-plugins.js'></script>
        <script src='./dist/ui/widget-scrollview.js'></script>
        <!--<link href="assets/css/flat-ui.css" rel="stylesheet">
        -->
        <!-- ####################################################################
        !important THIS FILE -= style.css =- ON BOTTOM OF BOOTSTRAP STYLEs  LIST
        It override bootstrap def. style
        ===================================================-->
        <link
        href="assets/css/style.css"
        rel="stylesheet"
        >

        <!-- FontAwesome styles (iconic font)-->
        <link
        href="assets/css/font-awesome.min.css"
        rel="stylesheet"
        >

    </head>

    <body>
        <script type="text/javascript">
            console.log(localStorage);
        </script>
        <!--################# START MAIN-WRAPPER ####################
        ============================================================ -->
        <div class="main-wrapper">

            <!-- ###################### START TOP MENU ####################
            ============================================================== -->

            <div class="nav-top">
                <!-- navbar-fixed-top -->
                <div
                class="navbar  navbar-inverse"
                id="top-nav"
                >
                    <!-- INNER-->
                    <div class="navbar-inner">
                        <div class="container">

                            <!-- LOGO -->
                            <div
                            class="brand"
                            style="text-align: center; margin: 0 auto;"
                            >
                                <span style="color: white"> <i class="fa-icon-cloud-upload"></i> 문제풀기 </span>
                                <!--<img src="assets/img/logo.png" alt="Logo">-->
                            </div>

                        </div>
                    </div>
                </div>
            </div>
            <!-- ############################ END TOP MENU ###############
            ========================================================== -->

            <div style="padding: 0px 10px 10px 10px">
                <form name="form1">
                    <input
                    class="input-block-level"
                    name="quiz_id"
                    type="text"
                    placeholder="문제 번호"
                    >
                </form>
                <div
                class="tabbable"
                style="margin-bottom: 9px;"
                >
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a
                            href="#1"
                            data-toggle="tab"
                            >객관식</a>
                        </li>
                        <li>
                            <a
                            href="#2"
                            data-toggle="tab"
                            >주관식</a>
                        </li>
                    </ul>
                    <div class="tab-content">
                        <div
                        class="tab-pane active"
                        id="1"
                        >

                            <div
                            class="btn-group"
                            data-toggle="buttons-radio"
                            style="width:100%;"
                            >
                                <label class="btn btn-primary "style="text-align: center;">
                                    <input type="radio" name="inputLogType" value="1">
                                    1번 </label>
                                <label class="btn btn-primary"style="text-align: center;">
                                    <input type="radio" name="inputLogType" value="2">
                                    2번 </label>
                                <label class="btn btn-primary"style="text-align: center;">
                                    <input type="radio" name="inputLogType"value="3">
                                    3번 </label>
                                <label class="btn btn-primary"style="text-align: center;">
                                    <input type="radio" name="inputLogType" value="4">
                                    4번 </label>
                                <label class="btn btn-primary"style="text-align: center;">
                                    <input type="radio" name="inputLogType" value="5">
                                    5번 </label>
                            </div>

                            <form
                            action=""
                            name="form2"
                            >
                                <textarea
																style="margin: 10px 0px 10px 0px"
																class="input-block-level"
																name="reason"
																placeholder="근거"
																 
														></textarea>                                                                                                                                																

 <input
                                type="button"
                                class="btn btn-primary btn-large btn-block"
                                value="제출"
                                onclick=fnSubmit(1)
                                >
                            </form>
                        </div>
                        <div
                        class="tab-pane"
                        id="2"
                        >
                            <form
                            action=""
                            name="form3"
                            >
                                <input
                                class="input-block-level"
                                name="answer"
                                type="text"
                                placeholder="정답"
                                >
                                <input
                                class="input-block-level"
                                name="reason"
                                type="text"
                                placeholder="근거"
                                >
                                <input
                                type="button"
                                class="btn btn-primary btn-large btn-block"
                                value="제출"
                                onclick=fnSubmit(2)
                                >
                            </form>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <!-- END: MAIN-WRAPPER-->

        <!-- Le javascript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->

        <script type="text/javascript">
            /*****
             *    로그인 함수 예제
             *****/
            var mAnswer;

            function fnSubmit(flag) {

                var mClass = localStorage.getItem("class");
                var mBan = localStorage.getItem("ban");
                var mNum = localStorage.getItem("num");
                var mName = localStorage.getItem("name");
                var mReason;
                var mQuiz = form1.quiz_id.value;
                if (flag == 1) {
                    mReason = form2.reason.value;
                    $(this).find('input:radio').attr('checked', true);
                    //alert($('input[name=inputLogType]:checked').val());
                    mAnswer = $('input[name=inputLogType]:checked').val();

                } else {
                    mAnswer = form3.answer.value;
                    mReason = form3.reason.value;
                }

                console.log(mClass + " " + mBan + " " + mNum + " " + mName + " " + mQuiz + " " + mAnswer + " " + mReason);

                if (!mQuiz) {
                    alert("문제 번호를 입력하세요..!!");
                    return;
                } else if (!mAnswer || mAnswer == 'undefined') {
                    alert("정답을 입력하세요..!!");
                    return;
                } else if (!mReason) {
                    alert("근거를 입력하세요..!!");
                    return;
                } else if (!mName) {
                    alert("이름을 입력하세요..!!");
                    return;
                } else {
                    submit();
                }

                function submit() {
                    var submitFlag = window.confirm(mQuiz + " 문제의 정답으로 " + mAnswer + "를 제출합니다.");
                    if (submitFlag) {
                        console.log(mClass + mNum + mName + mQuiz + mAnswer + mReason + "제출");
                        document.getElementsByName("quiz_id")[0].value = '';
                        document.getElementsByName("answer")[0].value = '';
                        document.getElementsByName("reason")[0].value = '';
                        document.getElementsByName("reason")[1].value = '';
                        
                    } else {
                    }
                }

            }

            //서버에서 정상적으로 로그인 되었다는 응답이 왔을 경우
            // document.getElementById("areaLogin").style.display = "none";

            // var objLoginInfo = document.getElementById("loginInfo");
            // 	objLoginInfo.innerHTML = objLogin.value + "님 로그인 되었습니다..!!";

            // 	document.getElementById("areaLogout").style.display = "";

            // end function fnLogin()

        </script>
        <?php
if($_requ)
        ?>
    </body>
</html>
