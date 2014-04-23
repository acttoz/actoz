<?php
include_once("_common.php");

// ---------------------------------------------------------------------------

# 이미지가 저장될 디렉토리의 전체 경로를 설정합니다.
# 끝에 슬래쉬(/)는 붙이지 않습니다.
# 주의: 이 경로의 접근 권한은 쓰기, 읽기가 가능하도록 설정해 주십시오.

# data/editor 디렉토리가 없는 경우가 있을수 있으므로 디렉토리를 생성하는 코드를 추가함. kagla 140305

@mkdir(G5_DATA_PATH.'/'.G5_EDITOR_DIR, G5_DIR_PERMISSION);
@chmod(G5_DATA_PATH.'/'.G5_EDITOR_DIR, G5_DIR_PERMISSION);

$ym = date('ym', G5_SERVER_TIME);

$data_dir = G5_DATA_PATH.'/'.G5_EDITOR_DIR.'/'.$ym;
$data_url = G5_DATA_URL.'/'.G5_EDITOR_DIR.'/'.$ym;

define("SAVE_DIR", $data_dir);

@mkdir(SAVE_DIR, G5_DIR_PERMISSION);
@chmod(SAVE_DIR, G5_DIR_PERMISSION);

# 위에서 설정한 'SAVE_DIR'의 URL을 설정합니다.
# 끝에 슬래쉬(/)는 붙이지 않습니다.

define("SAVE_URL", $data_url);

// ---------------------------------------------------------------------------
?>