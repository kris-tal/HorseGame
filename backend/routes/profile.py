import json

from flask import Blueprint, jsonify
from models import User, Horse
from flask_jwt_extended import jwt_required, get_jwt_identity

profile_bp = Blueprint('profile', __name__)
@profile_bp.route('/profile', methods=['GET'])
@jwt_required()
def get_profile_data():
    identity = get_jwt_identity()
    user_data = json.loads(identity)
    user = User.query.filter_by(username=user_data["username"]).first()
    return jsonify({"username": user.username, "email": user.email,
                    "created_at": user.created_at}), 200 # OK

# Get info about ones' horses
@profile_bp.route('/horses', methods=['GET'])
@jwt_required()
def get_user_horses():
    identity = get_jwt_identity()
    user_data = json.loads(identity)
    user = User.query.filter_by(username=user_data["username"]).first()

    if not user:
        return jsonify({"msg": "Invalid credentials!"}), 401

    horses = Horse.query.filter_by(owner_id=user.id).all()
    if not horses:
        return jsonify({}), 200
    horses_data = [
        {
            "id": horse.id,
            "name": horse.name,
            "race": horse.race,
            "speed": horse.speed,
            "stamina": horse.stamina
        } for horse in horses
    ]


    return jsonify(horses=horses_data), 200

