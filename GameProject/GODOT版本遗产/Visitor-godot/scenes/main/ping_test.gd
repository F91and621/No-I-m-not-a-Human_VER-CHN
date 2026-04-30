extends Node

@onready var http_request: HTTPRequest = $HTTPRequest
@onready var response_label: Label = $CanvasLayer/VBoxContainer/ResponseLabel

func _ready():
	# 启动时自动测试一次（可选）
	_on_ping_test_button_pressed()

func _on_ping_test_button_pressed():
	print("🚀 Godot 正在向 Java 后台发送 /ping...")
	
	# 关键：发送 GET 请求到你本地的 Java 后台
	var error = http_request.request("http://localhost:8080/ping")
	if error != OK:
		response_label.text = "❌ 请求发送失败！检查 Java 后台是否运行"
		print("请求错误码: ", error)

# 接收后台返回
func _on_http_request_request_completed(result, response_code, headers, body):
	if response_code == 200:
		var response_text = body.get_string_from_utf8()
		response_label.text = "✅ 后台返回: " + response_text
		print("🎉 Godot 与 Java 后台通信成功！返回：", response_text)
	else:
		response_label.text = "❌ 后台返回错误 (代码: %d)" % response_code
		print("HTTP 错误: ", response_code)
