from flask import Blueprint

from managers.identity_manager import get_user
from flask_jwt_extended import jwt_required, get_jwt_identity

from routes.profile import profile_bp

shop_bp = Blueprint('shop', __name__)

# Get info about ones' stable
@profile_bp.route('/shop', methods=['GET'])
@jwt_required()
def shop_listing():
    identity = get_jwt_identity()
    data, status = get_user(identity)
    if status != 200:
        return data, status
