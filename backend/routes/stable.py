from flask import Blueprint

from managers.file_manager import get_user_file, DataType
from managers.identity_manager import get_user
from flask_jwt_extended import jwt_required, get_jwt_identity

stable_bp = Blueprint('stable', __name__)

# Get info about ones' stable
@stable_bp.route('/stable', methods=['GET'])
@jwt_required()
def get_stable():
    identity = get_jwt_identity()
    user, status = get_user(identity)
    if status != 200:
        return user, status

    stable_data = get_user_file(user.id, DataType.Stable)
    return stable_data, 200


