from flask import Flask, jsonify
from config import Config
from models import db, User
from routes.profile import profile_bp
from routes.auth import auth_bp
from flask_jwt_extended import JWTManager

from routes.stable import stable_bp

app = Flask(__name__)
app.config.from_object(Config)

db.init_app(app)
jwt = JWTManager(app)


# Register all the blueprints of the backend
app.register_blueprint(auth_bp) # Authorization blueprint
app.register_blueprint(profile_bp) # Profile management blueprint
app.register_blueprint(stable_bp) # Stable management blueprint


with app.app_context():
    db.create_all()

if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0", port=4000)
