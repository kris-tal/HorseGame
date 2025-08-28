from flask import Blueprint

from database.models import HorseTypes
from managers.identity_manager import get_user
from flask_jwt_extended import jwt_required, get_jwt_identity
from managers.file_manager import get_user_file, DataType, create_default_file

shop_bp = Blueprint('shop', __name__)

def initialize_shop():
    horses_to_buy = []
    upgrades = []

    for horse in HorseTypes.query.all():
        if horse.default:
            # Owned by default → create upgrade entry
            upgrades.append({
                "name": horse.name,
                "price": horse.purchase_price  # example rule
            })
        else:
            # Not owned → available for purchase
            horses_to_buy.append({
                "name": horse.name,
                "price": horse.purchase_price  # example rule
            })

    shop_data = {
        "horses": horses_to_buy,
        "upgrades": upgrades
    }

    create_default_file(DataType.Shop, shop_data)


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

