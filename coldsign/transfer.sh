#!/usr/bin/env bash

XMR_CLI=./monero-wallet-cli
XMR_CLI=./cli

#ADDRESS        = "A2PZyjDZT1JjnmdSXAsPb5hv9ne7Tty9b2qaLrw7s3zSMsxAsd8fcAoVWt2xFapSX4UYDce7gDeHjXRyKb5t45TcGhYmY9y";
#VIEW_KEY       = "6e6749ca79e1dedcf514b7fe52035b8cb04c693afe456d36b91f56c08842260a";
#SPEND_KEY      = "d7a66a48134551b44739f372ae42c2ce9675cb86286e5c69d07e47d90ff3b308";
#TARGET_ADDRESS = "9u7McB2tQJQQbd2jrncdRW43m72Y5vbrQDt459FQB7SHbyCCnwuaBhSgvmdnQykqQecpguE8Csnms9nzoAK5QxzMGF75xqj";

function hot_create() {
    ${XMR_CLI} --testnet --generate-from-view-key hot --password 1
}

function cold_create() {
    ${XMR_CLI} --testnet --generate-from-spend-key cold --password 1 --daemon-address dummy:1000
}

function hot_export_output() {
    ${XMR_CLI} --testnet --wallet-file hot  --password 1 --command export_outputs hot_export_outputs.hex
}

function hot_make_unsigned_tx() {
    ${XMR_CLI} --testnet --wallet-file hot  --password 1 --command transfer 9u7McB2tQJQQbd2jrncdRW43m72Y5vbrQDt459FQB7SHbyCCnwuaBhSgvmdnQykqQecpguE8Csnms9nzoAK5QxzMGF75xqj 0.01

}

function hot_submit_signed_tx() {
    ${XMR_CLI} --testnet --wallet-file hot  --password 1 --command submit_transfer
}


function cold_sign_tx() {
    ${XMR_CLI} --testnet --wallet-file cold  --password 1 --command sign_transfer
}

function cold_import_outputs_export_key_images() {
    ${XMR_CLI} --testnet --wallet-file cold --password 1  --command import_outputs hot_export_outputs.hex
    ${XMR_CLI} --testnet --wallet-file cold --password 1  --command export_key_images cold_export_key_images.hex
}

function hot_import_key_images() {
    ${XMR_CLI} --testnet --wallet-file hot --password 1  --command import_key_images cold_export_key_images.hex
}

function transfer() {
    hot_make_unsigned_tx
    cold_sign_tx
    hot_submit_signed_tx
}


function export_import() {
    hot_export_output
    cold_import_outputs_export_key_images
    hot_import_key_images
}

function main() {

    if [ ! -f cold ]; then
        cold_create
    fi

    if [ ! -f hot ]; then
        hot_create
    fi

    transfer

    export_import

    transfer
}

main
