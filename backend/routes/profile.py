import json

from flask import Blueprint, jsonify
from database.models import User
from flask_jwt_extended import jwt_required, get_jwt_identity

profile_bp = Blueprint('profile', __name__)
@profile_bp.route('/profile', methods=['GET'])
@jwt_required()
def get_profile_data():
    identity = get_jwt_identity()
    user_data = json.loads(identity)
    user = User.query.filter_by(username=user_data["username"]).first()
    return jsonify({"username": user.username, "email": user.email, "money":user.money,
                    "prestige":user.prestige, "created_at": user.created_at}), 200 # OK

