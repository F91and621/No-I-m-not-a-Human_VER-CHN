extends Node

@onready var http_request: HTTPRequest = $HTTPRequest
@onready var response_label: Label = $CanvasLayer/VBoxContainer/ResponseLabel
@onready var directional_light: DirectionalLight3D = $Room/DirectionalLight3D
@onready var world_env: WorldEnvironment = $WorldEnvironment

var is_day: bool = true

func _ready():
	print("🎮 MainScene 已启动 - 简单房间 + 基础昼夜系统")
	_on_ping_test_button_pressed()  # 自动 ping 测试

# ==================== 昼夜切换 ====================
func _on_day_night_button_pressed():
	is_day = !is_day
	if is_day:
		# 白天设置
		directional_light.light_color = Color(1.0, 0.95, 0.8)  # 暖白阳光
		directional_light.light_energy = 1.2
		world_env.environment.ambient_light_color = Color(0.6, 0.75, 1.0)
		print("☀️ 已切换到白天")
		response_label.text = "☀️ 当前是白天"
	else:
		# 夜晚设置
		directional_light.light_color = Color(0.4, 0.5, 0.9)  # 冷蓝月光
		directional_light.light_energy = 0.4
		world_env.environment.ambient_light_color = Color(0.05, 0.05, 0.15)
		print("🌙 已切换到夜晚")
		response_label.text = "🌙 当前是夜晚"
	
	# 可选：让光照方向微微变化，模拟太阳/月亮位置
	directional_light.rotation_degrees.x = -45 if is_day else -20

# ==================== 原有 ping 测试保持不变 ====================
func _on_ping_test_button_pressed():
	print("🚀 Godot 正在向 Java 后台发送 /ping...")
	var error = http_request.request("http://localhost:8080/ping")
	if error != OK:
		response_label.text = "❌ 请求发送失败！检查 Java 后台是否运行"

func _on_http_request_request_completed(result, response_code, headers, body):
	if response_code == 200:
		var response_text = body.get_string_from_utf8()
		response_label.text = "✅ 后台返回: " + response_text
		print("🎉 Godot 与 Java 后台通信成功！返回：", response_text)
	else:
		response_label.text = "❌ 后台返回错误 (代码: %d)" % response_code
