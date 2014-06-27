<?php
if (!defined('_GNUBOARD_')) exit; // 개별 페이지 접근 불가

function editor_html($id, $content, $is_dhtml_editor=true)
{
    global $config;
    global $editor_width, $editor_height;
    static $js = true;

    $width  = isset($editor_width)  ? $editor_width  : "100%";
    $height = isset($editor_height) ? $editor_height : "250px";
    if (defined(G5_PUNYCODE))
        $editor_url = G5_PUNYCODE.'/'.G5_EDITOR_DIR.'/'.$config['cf_editor'];
    else
        $editor_url = G5_EDITOR_URL.'/'.$config['cf_editor'];

    $html = "";
    if ($is_dhtml_editor) {
        if ($js) {
            $html .= "<script src=\"{$editor_url}/cheditor.js\"></script>";
        }
        $html .= "<script>\n";
        $html .= "var ed_{$id} = new cheditor('ed_{$id}');\n";
        $html .= "ed_{$id}.config.editorWidth = \"{$width}\";\n";             
        $html .= "ed_{$id}.config.editorHeight = \"{$height}\";\n";           
        $html .= "ed_{$id}.config.imgReSize = false;\n";                    
        $html .= "ed_{$id}.config.fullHTMLSource = false;\n";               
        $html .= "ed_{$id}.config.editorPath = \"{$editor_url}\";\n"; 
        $html .= "ed_{$id}.inputForm = \"tx_{$id}\";\n";                      
        $html .= "</script>\n";                                             
        $html .= "<span class=\"sound_only\">웹에디터 시작</span>";
        $html .= "<textarea name=\"{$id}\" id=\"tx_{$id}\" style=\"display:none;\">{$content}</textarea>\n";
        $html .= "\n<span class=\"sound_only\">웹 에디터 끝</span>";
        $html .= "<script>ed_{$id}.run();</script>\n";
    } else {
        $html .= "<textarea id=\"$id\" name=\"$id\" style=\"width:{$width};height:{$height};\" maxlength=\"65536\">$content</textarea>\n";
    }
    return $html;
}


// textarea 로 값을 넘긴다. javascript 반드시 필요
function get_editor_js($id, $is_dhtml_editor=true)
{
    if ($is_dhtml_editor) {
        return "document.getElementById('tx_{$id}').value = ed_{$id}.outputBodyHTML();\n";
    } else {
        return "var {$id}_editor = document.getElementById('{$id}');\n";
    }
}


//  textarea 의 값이 비어 있는지 검사
function chk_editor_js($id, $is_dhtml_editor=true)
{
    if ($is_dhtml_editor) {
        return "if (document.getElementById('tx_{$id}') && !ed_{$id}.outputBodyHTML()) { alert(\"내용을 입력해 주십시오.\"); ed_{$id}.returnFalse(); return false; }\n";
    } else {
        return "if (!{$id}_editor.value) { alert(\"내용을 입력해 주십시오.\"); {$id}_editor.focus(); return false; }\n";
    }
}
?>