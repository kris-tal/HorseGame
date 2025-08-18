from flask import Blueprint

from managers.identity_manager import get_user
from flask_jwt_extended import jwt_required, get_jwt_identity
from managers.file_manager import get_user_file, DataType


shop_bp = Blueprint('shop', __name__)

# Get info about ones' stable
@shop_bp.route('/shop', methods=['GET'])
@jwt_required()
def shop_listing():
    identity = get_jwt_identity()
    user, status = get_user(identity)
    if status != 200:
        return user, status

    shop_data = get_user_file(user.id, DataType.Shop)
    return shop_data, 200
