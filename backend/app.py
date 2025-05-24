from flask import Flask, jsonify
from config import Config
from models import db
from routes.profile import profile_bp
from routes.auth import auth_bp
from flask_jwt_extended import JWTManager

app = Flask(__name__)
app.config.from_object(Config)

db.init_app(app)
jwt = JWTManager(app)

app.register_blueprint(auth_bp)
app.register_blueprint(profile_bp)


@app.route('/', methods=['GET'])
def index():
    return jsonify({"msg": "Hello, cruel world!"}), 200

if __name__ == '__main__':
    with app.app_context():
        db.create_all()
    app.run(debug=True)
