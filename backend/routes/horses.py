from flask import Blueprint, jsonify

from managers.identity_manager import get_user
from database.models import Horse, HorseTypes, db
from flask_jwt_extended import jwt_required, get_jwt_identity

horses_bp = Blueprint('horses', __name__)

# Get info about ones' horses
@horses_bp.route('/horses', methods=['GET'])
@jwt_required()
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
            "name": horse.name_id,
            "level": horse.level
        } for horse in horses
    ]

    return jsonify(horses=horses_data), 200


@horses_bp.route('/horses/<int:id>', methods=['GET'])
@jwt_required()
def get_user_horse(horse_id):
    identity = get_jwt_identity()
    data, status = get_user(identity)
    if status != 200:
        return data, status

    horse = Horse.query.filter_by(owner_id=data.id, id = horse_id).first()
    if not horse:
        return jsonify({"msg":"Horse not found or belongs to a different user"}), 404
    return jsonify(
        {
            "id": horse.id,
            "name": horse.name,
            "level": horse.level
        }), 200

# Helper functions (for shop functionalities)
def add_horse(user_id, name_id):
    new_horse = Horse(owner_id=user_id, name_id=name_id)
    db.session.add(new_horse)
    db.session.commit()
    return new_horse.id

def upgrade_user_horse(user_id, name_id):
    horse = Horse.query.filter_by(owner_id=user_id, name_id = name_id).first()
    if horse is None:
        return
    horse.level += 1
    db.session.commit()


