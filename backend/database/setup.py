import os

from database.models import db, HorseTypes
from managers.file_manager import CONTENT_DIR, DEFAULT_DATA_DIR, DEFAULT_FILES, DataType
from routes.shop import initialize_shop

DEFAULT_HORSE_TYPES = [
    {"name": "Ariel", "purchase_price": 500, "default":True},
    {"name": "Ash", "purchase_price": 800, "default":False},
    {"name": "Twilight", "purchase_price": 1200, "default":False},
]


def initialize():
    # seed defaults if not present
    if not HorseTypes.query.first():
        for horse in DEFAULT_HORSE_TYPES:
            db.session.add(HorseTypes(**horse))

    shop_path = os.path.join(CONTENT_DIR, DEFAULT_DATA_DIR, DEFAULT_FILES[DataType.Shop])
    if not os.path.exists(shop_path):
        initialize_shop()