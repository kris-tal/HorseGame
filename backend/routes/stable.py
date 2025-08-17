from flask import Blueprint

from managers.identity_manager import get_user
from flask_jwt_extended import jwt_required, get_jwt_identity

from routes.profile import profile_bp

stable_bp = Blueprint('stable', __name__)

# Get info about ones' stable
@profile_bp.route('/stable', methods=['GET'])
@jwt_required()
def get_stable():
    identity = get_jwt_identity()
    data, status = get_user(identity)
    if status != 200:
        return data, status


