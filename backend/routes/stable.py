import json

from flask import Blueprint, request, jsonify
from models import User
from flask_jwt_extended import jwt_required, get_jwt_identity

from routes.profile import profile_bp

stable_bp = Blueprint('stable', __name__)

# Get info about ones' stable
@profile_bp.route('/stable', methods=['GET'])
@jwt_required()
def get_stable():
    identity = get_jwt_identity()
    user_data = json.loads(identity)
    user = User.query.filter_by(username=user_data["username"]).first()

