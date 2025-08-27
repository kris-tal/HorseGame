from models import db, HorseTypes

DEFAULT_HORSE_TYPES = [
    {"name": "Ariel", "purchase_price": 500},
    {"name": "Ash", "purchase_price": 800},
    {"name": "Twilight", "purchase_price": 1200},
]


def initialize():
    # seed defaults if not present
    if not HorseTypes.query.first():
        for horse in DEFAULT_HORSE_TYPES:
            db.session.add(HorseTypes(**horse))
