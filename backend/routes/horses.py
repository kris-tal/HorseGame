from flask import Blueprint, jsonify, request

from managers.identity_manager import get_user
from database.models import Horse, HorseTypes, db
from flask_jwt_extended import jwt_required, get_jwt_identity

horses_bp = Blueprint('horses', __name__)

# Get info about ones' horses
@jwt_required()
@horses_bp.route('/horses', methods=['GET'])
def get_user_horses():
    identity = get_jwt_identity()
    data, status = get_user(identity)
    if status != 200:
        return data, status

    horses = Horse.query.filter_by(owner_id=data.id).all()
    if not horses:
        return jsonify({}), 200
    horses_data = [
        {
            "id": horse.id,
            "name": horse.name,
            "level": horse.level
        } for horse in horses
    ]

    return jsonify(horses=horses_data), 200


def add_horse(user_id, name):
    horse = HorseTypes.query.filter_by(name=name).first()
    if horse is None:
        return None
    new_horse = Horse(owner_id=user_id, name=name)
    Horse.query.add(new_horse)
    db.session.commit()
    return new_horse.id


def upgrade_horse(horse_id):
    horse = Horse.query.filter_by(id=horse_id).first()
    if horse is None:
        return None
    horse.level += 1
    db.session.commit()
