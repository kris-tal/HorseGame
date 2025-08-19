from flask import Blueprint, jsonify, request

from managers.identity_manager import get_user
from database.models import Horse
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
            "race": horse.race,
            "speed": horse.speed,
            "stamina": horse.stamina
        } for horse in horses
    ]

    return jsonify(horses=horses_data), 200


# Structure for sample request
# { id:1, name:"Ariel" };
@jwt_required()
@horses_bp.route('/horses/name', methods=['POST'])
def rename_horse():
    identity = get_jwt_identity()
    data, status = get_user(identity)
    if status != 200:
        return data, status
    horse_id = request.json['id']
    horse_name = request.json['name']
    if not horse_id or not horse_name:
        return jsonify({"msg":"Invalid request"}), 400

    horses = Horse.query.filter_by(owner_id=data.id, id=horse_id).all()
    if not horses:
        return jsonify({"msg":"Horse not found"}), 404

    horse = horses[0]
    horse.name = request.json['name']
